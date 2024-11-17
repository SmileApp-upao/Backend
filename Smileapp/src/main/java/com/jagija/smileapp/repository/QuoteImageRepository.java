package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.QuoteImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteImageRepository extends JpaRepository<QuoteImage, Integer> {
    List<QuoteImage> findByQuoteId(Integer quoteId);
}
