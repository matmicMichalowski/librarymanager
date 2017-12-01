//package matmic.librarymaneger.converters;
//
//import lombok.Synchronized;
//import matmic.librarymaneger.commands.RoleCommand;
//import matmic.librarymaneger.model.rolemodel.Role;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RoleToRoleCommand implements Converter<Role, RoleCommand> {
//
//    private final EmployeeRoleToEmployeeRoleCommand employeeRoleToEmployeeRoleCmd;
//
//    public RoleToRoleCommand(EmployeeRoleToEmployeeRoleCommand employeeRoleToEmployeeRoleCmd) {
//        this.employeeRoleToEmployeeRoleCmd = employeeRoleToEmployeeRoleCmd;
//    }
//
//    @Synchronized
//    @Nullable
//    @Override
//    public RoleCommand convert(Role role) {
//        if(role == null) {
//            return null;
//        }
//
//        final RoleCommand roleCommand = new RoleCommand();
//        roleCommand.setId(role.getId());
//        roleCommand.setName(role.getName());
//
//        if(role.getEmployeeRoles() != null && role.getEmployeeRoles().size() > 0){
//            role.getEmployeeRoles().forEach( employeeRole -> roleCommand.getEmployeeRoles()
//                    .add(employeeRoleToEmployeeRoleCmd.convert(employeeRole)));
//        }
//
//
//        return roleCommand;
//    }
//}
