package matmic.librarymanager.repositories;

import matmic.librarymanager.model.rolemodel.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long>{
    ArrayList<EmployeeRole> findEmployeeRolesByEmployee_Id(Long id);
}
