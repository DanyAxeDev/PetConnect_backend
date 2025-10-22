package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.AdoptionStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionStoryRepository extends JpaRepository<AdoptionStory, Long> {
    
    List<AdoptionStory> findByUserId(Long userId);
    
    List<AdoptionStory> findByApprovedTrue();
    
    Page<AdoptionStory> findByApprovedTrue(Pageable pageable);
    
    Page<AdoptionStory> findByUserId(Long userId, Pageable pageable);
    
    Page<AdoptionStory> findByApproved(Boolean approved, Pageable pageable);
    
    @Query("SELECT as FROM AdoptionStory as WHERE as.approved = true ORDER BY as.createdAt DESC")
    List<AdoptionStory> findApprovedStoriesOrderByDateDesc();
}
