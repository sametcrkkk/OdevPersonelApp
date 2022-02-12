package com.bilgeadam.odevpersonelapp.controller;

import com.bilgeadam.odevpersonelapp.entity.Personel;
import com.bilgeadam.odevpersonelapp.exception.PersonelNotFoundException;
import com.bilgeadam.odevpersonelapp.pojo.Bolum;
import com.bilgeadam.odevpersonelapp.pojo.Sehir;
import com.bilgeadam.odevpersonelapp.repository.PersonelRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonelController {

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private EurekaClient client;

    @GetMapping("/personel/{id}")
    public Personel getKisi(@PathVariable("id") long id)
    {
        Personel personel = null;
        Optional<Personel> personelDB = personelRepository.findById(id);

        if(personelDB.isPresent())
        {
            personel = personelDB.get();
            return personel;
        }
        else
        {
            throw new PersonelNotFoundException(id + " nolu Personel bulunamadı!");
        }
    }

    @GetMapping("/personel")
    public List<Personel> getTumPersonel()
    {
        return personelRepository.findAll();
    }

    @GetMapping("/personel-tam/{id}")
    public String getPersonelTam(@PathVariable("id") long id)
    {
        Personel personel = getKisi(id);

        Bolum bolum = getBolum(personel.getBolumNo());

        Sehir sehir = getSehir(bolum.getSehirNo());

        return personel.getAd()+" "+personel.getSoyad()+" "+bolum.getAd()+" "+sehir.getAd();

    }

    @GetMapping("/personel/bolum/{bolumNo}")
    public List<Personel> getPersonelByBolumNo(@PathVariable("bolumNo") long bolumNo)
    {
        return personelRepository.findPersonelByBolumNo(bolumNo);
    }

    private Bolum getBolum(long bolumNo)
    {
        //String bolumURL = "http://localhost:8230";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo = client.getNextServerFromEureka("odev-bolum-app", false);

        String bolumURL = instanceInfo.getHomePageUrl();

        Bolum bolum = restTemplate.getForObject(bolumURL+"/bolum/" + bolumNo, Bolum.class);

        return bolum;
    }

    private Sehir getSehir(long sehirNo)
    {
        //String sehirURL = "http://localhost:8240";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo = client.getNextServerFromEureka("odev-sehir-app", false);

        String sehirURL = instanceInfo.getHomePageUrl();


        Sehir sehir = restTemplate.getForObject(sehirURL+"/sehir/" + sehirNo, Sehir.class);

        return sehir;
    }



}