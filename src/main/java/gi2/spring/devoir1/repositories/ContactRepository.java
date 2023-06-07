package gi2.spring.devoir1.repositories;

import gi2.spring.devoir1.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where lower(c.firstname) like lower(concat('%', :name, '%')) " +
            "or lower(c.lastname) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.lastname, '%', c.firstname, '%')) " +
            "or lower(:name) like lower(concat('%', c.firstname, '%', c.lastname, '%'))")
    Page<Contact> searchByName(@Param("name") String name, Pageable pageable);

    @Query("select c from Contact c where (lower(c.firstname) like lower(concat('%', :name, '%')) " +
            "or lower(c.lastname) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.lastname, '%', c.firstname, '%')) " +
            "or lower(:name) like lower(concat('%', c.firstname, '%', c.lastname, '%'))) " +
            "and c.personnalNumber like concat('%', :tel, '%')")
    Page<Contact> searchByNameAndPersonnalTel(@Param("name") String name, @Param("tel") String tel, Pageable pageable);

    @Query("select c from Contact c where (lower(c.firstname) like lower(concat('%', :name, '%')) " +
            "or lower(c.lastname) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.lastname, '%', c.firstname, '%')) " +
            "or lower(:name) like lower(concat('%', c.firstname, '%', c.lastname, '%'))) " +
            "and c.professionalNmuber like concat('%', :tel, '%')")
    Page<Contact> searchByNameAndProTel(@Param("name") String name, @Param("tel") String tel, Pageable pageable);

    @Query("select c from Contact c where c.professionalNmuber like concat('%', :tel, '%')")
    Page<Contact> searchByProTel(@Param("tel") String tel, Pageable pageable);

    @Query("select c from Contact c where c.personnalNumber like concat('%', :tel, '%')")
    Page<Contact> searchByPersonnalTel(@Param("tel") String tel, Pageable pageable);

    @Query("select c from Contact c where lower(c.firstname) like lower(concat('%', :name, '%')) " +
            "or lower(c.lastname) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.lastname, '%', c.firstname, '%')) " +
            "or lower(:name) like lower(concat('%', c.firstname, '%', c.lastname, '%'))")
    List<Contact> searchByName(@Param("name") String name, Sort sort);

}
