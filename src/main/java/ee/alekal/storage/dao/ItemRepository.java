package ee.alekal.storage.dao;

import ee.alekal.storage.model.jpa.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByParentItemId(Long parentItemId);
}
