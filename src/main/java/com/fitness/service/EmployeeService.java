package com.fitness.service;

import com.fitness.dao.EmployeeDAO;
import com.fitness.enums.EmployeeArea;
import com.fitness.enums.Gender;
import com.fitness.enums.UserRol;
import com.fitness.factory.DAOFactory;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.user.Employee;
import com.fitness.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeService {
    private final EmployeeDAO employeeDAO = DAOFactory.getDAO(EmployeeDAO.class);
    private final UserService userService = ServiceFactory.getService(UserService.class);

    public EmployeeService() {
        if (getAllEmployees().isEmpty()) {
            createEmployees().forEach(employeeDAO::create);
        }
    }

    public boolean addEmployee(Employee employee) {
        return employeeDAO.create(employee);
    }

    public boolean updateEmployee(Employee employee) {
        return employeeDAO.update(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public List<Employee> getAllTrainers() {
        return employeeDAO.findAll()
                .stream()
                .filter(e -> e.getArea().equals(EmployeeArea.TRAINERS))
                .toList();
    }

    private List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        // Datos base para generar variedad
        String[] firstnames = {"Carlos", "María", "Luis", "Ana", "Jorge", "Lucía", "Pedro", "Elena", "Raúl", "Patricia",
                "Miguel", "Sofía", "Andrés", "Gabriela", "José", "Rosa", "Fernando", "Carmen", "Alberto", "Claudia",
                "Héctor", "Daniela", "Ricardo", "Paola", "Manuel", "Mónica", "Óscar", "Verónica", "Hugo", "Diana"};
        String[] lastnames = {"Pérez", "García", "Rodríguez", "Fernández", "López", "Martínez", "Castro", "Guzmán",
                "Torres", "Ramírez", "Flores", "Díaz", "Mendoza", "Salazar", "Morales", "Ruiz", "Chávez", "Cordero",
                "Reyes", "Santos", "Campos", "Peña", "Álvarez", "Herrera", "Vargas", "Medina", "Rojas", "Navarro",
                "Silva", "Ortiz"};
        String[] addresses = {
                "Av. Arequipa 123, Lima", "Jr. Los Pinos 456, Miraflores", "Av. La Marina 789, San Miguel",
                "Calle Las Flores 101, San Borja", "Av. Javier Prado 202, San Isidro", "Jr. Independencia 303, Barranco",
                "Av. Angamos 404, Surquillo", "Av. Brasil 505, Jesús María", "Av. Grau 606, Cercado de Lima",
                "Av. Universitaria 707, Los Olivos"
        };
        Gender[] genders = Gender.values();

        // 22 entrenadores
        for (int i = 0; i < 22; i++) {
            String firstname = firstnames[random.nextInt(firstnames.length)];
            String lastname = lastnames[random.nextInt(lastnames.length)];
            String email = firstname.toLowerCase() + "." + lastname.toLowerCase() + i + "@gym.com";

            User user = userService.register(email, "123456", UserRol.EMPLOYEE);

            Employee e = new Employee();
            e.setFirstname(firstname);
            e.setLastname(lastname);
            e.setDni(String.format("%08d", 10000000 + i));
            e.setNumberPhone(String.format("9%08d", i + 1000));
            e.setAddress(addresses[random.nextInt(addresses.length)]);
            e.setAge(20 + random.nextInt(25));
            e.setGender(genders[random.nextInt(genders.length)]);
            e.setUser(user);
            e.setArea(EmployeeArea.TRAINERS);

            employees.add(e);
        }

        // 4 ventas
        for (int i = 0; i < 4; i++) {
            String firstname = firstnames[random.nextInt(firstnames.length)];
            String lastname = lastnames[random.nextInt(lastnames.length)];
            String email = firstname.toLowerCase() + "." + lastname.toLowerCase() + (i + 22) + "@ventas.com";

            User user = userService.register(email, "123456", UserRol.EMPLOYEE);

            Employee e = new Employee();
            e.setFirstname(firstname);
            e.setLastname(lastname);
            e.setDni(String.format("%08d", 20000000 + i));
            e.setNumberPhone(String.format("9%08d", i + 2000));
            e.setAddress(addresses[random.nextInt(addresses.length)]);
            e.setAge(22 + random.nextInt(20));
            e.setGender(genders[random.nextInt(genders.length)]);
            e.setUser(user);
            e.setArea(EmployeeArea.VENTAS);

            employees.add(e);
        }

        // 4 IT
        for (int i = 0; i < 4; i++) {
            String firstname = firstnames[random.nextInt(firstnames.length)];
            String lastname = lastnames[random.nextInt(lastnames.length)];
            String email = firstname.toLowerCase() + "." + lastname.toLowerCase() + (i + 26) + "@it.com";

            User user = userService.register(email, "123456", UserRol.ADMIN);

            Employee e = new Employee();
            e.setFirstname(firstname);
            e.setLastname(lastname);
            e.setDni(String.format("%08d", 30000000 + i));
            e.setNumberPhone(String.format("9%08d", i + 3000));
            e.setAddress(addresses[random.nextInt(addresses.length)]);
            e.setAge(24 + random.nextInt(18));
            e.setGender(genders[random.nextInt(genders.length)]);
            e.setUser(user);
            e.setArea(EmployeeArea.IT);

            employees.add(e);
        }

        return employees;
    }
}
