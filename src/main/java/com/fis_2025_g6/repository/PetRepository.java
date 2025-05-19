package com.fis_2025_g6.repository;

import com.fis_2025_g6.AdoptionStatus;
import com.fis_2025_g6.entity.Pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByRefugeId(Long refugeId);

    @Query(
        "SELECT p FROM Pet p WHERE "
        + "(:species IS NULL OR p.species = :species) AND "
        + "(:age IS NULL OR p.age = :age) AND "
        + "(:sex IS NULL OR p.sex = :sex) AND "
        + "(:status IS NULL OR p.status = :status)"
    )
    List<Pet> filter(
        @Param("species") String species,
        @Param("age") Integer age,
        @Param("sex") String sex,
        @Param("status") AdoptionStatus status
    );
}
