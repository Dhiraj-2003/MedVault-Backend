package com.healthcare.medVault.repository;

import com.healthcare.medVault.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

}
