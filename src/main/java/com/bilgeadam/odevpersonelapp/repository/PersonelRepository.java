package com.bilgeadam.odevpersonelapp.repository;

import com.bilgeadam.odevpersonelapp.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonelRepository extends JpaRepository<Personel, Long> {

        Personel findPersonelByAd(String ad);
        Personel findPersonelBySoyad(String soyad);
        List<Personel> findPersonelByBolumNo(long bolumNo);

    }

