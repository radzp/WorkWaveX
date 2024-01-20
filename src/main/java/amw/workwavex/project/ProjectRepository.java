package amw.workwavex.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    List<Project> findByProjectName(String name);

    @Query("SELECT p FROM Project p JOIN p.projectMembers m WHERE m.id = :userId")
    List<Project> findByMemberId(Integer userId);
}
