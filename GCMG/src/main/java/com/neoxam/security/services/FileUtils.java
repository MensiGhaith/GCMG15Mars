package com.neoxam.security.services;




import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.neoxam.models.DBFile;
import com.neoxam.repository.DBFileRepository;



@Service
public class FileUtils {
	@Autowired
	DBFileRepository fileRepo;
	
	public boolean uploadImage(MultipartFile file,String tag,String dep) {
		DBFile image = createFile(file);
		if (image == null)
			return false;
		image.setFileTag(tag);
		image.setDepartement(dep);
		image.setActive(true);
		fileRepo.save(image);
		return true;
	}
	
	public DBFile getFile( String fileTag) throws IOException {

		final Optional<DBFile> retrievedFile = fileRepo.findByFileTag(fileTag);
		DBFile img = new DBFile(retrievedFile.get().getName(), retrievedFile.get().getFileType(),
				decompressBytes(retrievedFile.get().getFileContent()),retrievedFile.get().getAddedAt(),retrievedFile.get().getFileTag(),retrievedFile.get().getDepartement());
		return img;
	}

	public static DBFile createFile(MultipartFile file) {
		DBFile myfile = new DBFile();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String[] nameAndType = fileName.split("\\.");

		if (nameAndType.length != 2)
			return null;

		try {
			myfile.setFileContent(compressBytes(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		myfile.setAddedAt(new Date(LocalDateTime.now().getMinute()));// please change all set<date> like this
		myfile.setName(nameAndType[0]);
		myfile.setFileType(nameAndType[1]);
		
		return myfile;
	}

	public static String getFileName(DBFile file) {
		return file.getName() + "." + file.getFileType();
	}

	// not used
	public static ResponseEntity<?> downloadFile(DBFile file) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		String filename = file.getName() + "." + file.getFileType();

		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(file.getFileContent(), headers, HttpStatus.OK);
	}
	
	// compress the image bytes before storing it in the database
			public static byte[] compressBytes(byte[] data) {
				Deflater deflater = new Deflater();
				deflater.setInput(data);
				deflater.finish();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				try {
					outputStream.close();
				} catch (IOException e) {
				}
				System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

				return outputStream.toByteArray();
			}
			// uncompress the image bytes before returning it to the angular application
			public static byte[] decompressBytes(byte[] data) {
				Inflater inflater = new Inflater();
				inflater.setInput(data);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				try {
					while (!inflater.finished()) {
						int count = inflater.inflate(buffer);
						outputStream.write(buffer, 0, count);
					}
					outputStream.close();
				} catch (IOException ioe) {
				} catch (DataFormatException e) {
				}
				return outputStream.toByteArray();
			}
}
