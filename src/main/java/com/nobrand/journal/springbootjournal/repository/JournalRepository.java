package com.nobrand.journal.springbootjournal.repository;

import com.nobrand.journal.springbootjournal.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;


/*
* To use JPA, make marker-interface, which extends JpaRepository interface.
* Spring data engine recognizes by itself.
* */
public interface JournalRepository extends JpaRepository<Journal, Long> {

}
