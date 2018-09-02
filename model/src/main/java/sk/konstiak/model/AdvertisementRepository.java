package sk.konstiak.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

    List<Advertisement> findByTitle(String title);
}
