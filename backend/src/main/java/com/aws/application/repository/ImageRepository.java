package com.aws.application.repository;

import com.aws.application.domain.Image;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaSpecificationExecutor<Image>, PagingAndSortingRepository<Image, Long> {

}