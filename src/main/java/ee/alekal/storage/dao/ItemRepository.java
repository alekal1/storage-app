package ee.alekal.storage.dao;

import ee.alekal.storage.model.jpa.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByParentItemId(Long parentItemId);

    @Query("SELECT i FROM Item i" +
            " WHERE i.person.username = ?1" +
            " AND LOWER(i.name) LIKE ?2%")
    List<Item> getAllBySearchQueryAndUsername(String userName, String searchQuery);
}
