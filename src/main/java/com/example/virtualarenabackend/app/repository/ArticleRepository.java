package com.example.virtualarenabackend.app.repository;

import com.example.virtualarenabackend.app.document.Article;
import com.example.virtualarenabackend.app.document.Comment;
import com.example.virtualarenabackend.app.document.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    Page<Article> findAllByOrderByPublishedAtDesc(Pageable pageable);

    @Query(value = """
    [
      {
        $addFields: {
          engagementScore: {
            $add: [
              { $size: '$hearts' },
              { $multiply: [{ $size: '$comments' }, 2] }, 
              { $size: '$bookmarks' }
            ]
          }
        }
      },
      {
        $match: {
          engagementScore: {
            $gt: 0 
          }
        }
      },
      {
        $sort: {
          engagementScore: -1,
          publishedAt: -1
        }
      },
      {
        $limit: 5
      }
    ]
    """)
    List<Article> findFeaturedArticles();

    Page<Article> findByPublisherIdOrderByPublishedAtDesc(String publisherId, Pageable pageable);
    @Query("{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'shortDescription': {$regex: ?0, $options: 'i'}}]}")
    Page<Article> searchArticles(String query, Pageable pageable);

    Page<Article> findByBookmarksContainingOrderByPublishedAtDesc(String userId, Pageable pageable);

    List<Article> findByPublishedAtAfter(LocalDateTime thirtyDaysAgo);


}

