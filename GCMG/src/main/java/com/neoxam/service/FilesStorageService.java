package com.neoxam.service;


import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;




import java.util.stream.Stream;



public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();
}