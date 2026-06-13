package br.com.aliancapsicossocial.repositories;

import br.com.aliancapsicossocial.models.FAQItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FAQItemRepository extends JpaRepository<FAQItem, UUID>, JpaSpecificationExecutor<FAQItem> {
}
