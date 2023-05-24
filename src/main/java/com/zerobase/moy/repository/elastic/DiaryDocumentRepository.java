package com.zerobase.moy.repository.elastic;

import com.zerobase.moy.data.domain.DiaryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryDocumentRepository extends ElasticsearchRepository<DiaryDocument, Long> {

  Page<DiaryDocument> findAllByDeletedIsFalseAndIsPublicIsTrue(Pageable pageable);

  @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^3\", \"content\"]}}")
  Page<DiaryDocument> searchByFields(String query, Pageable pageable);
}
