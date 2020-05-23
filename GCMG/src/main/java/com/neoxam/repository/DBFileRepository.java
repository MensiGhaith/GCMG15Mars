package com.neoxam.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoxam.models.DBFile;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, Long> {
	Optional<DBFile> findByFileTag(String name);
	Boolean existsByFileTag(String tag);
	
	Boolean existsByNameAndDepartement(String name,String dep);
	Optional<DBFile> findByNameAndDepartement(String FileName,String dep);


}