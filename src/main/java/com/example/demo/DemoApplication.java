package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class DemoApplication {

    private final CustomerRepository customerRepository;

    public DemoApplication(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age){

    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);

    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer id,
                               @RequestBody NewCustomerRequest request){
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(c ->{
            c.setName(request.name());
            c.setEmail(request.email());
            c.setAge(request.age());
            customerRepository.save(c);
        });

    }
//    @GetMapping("/greet")
//    public GreetResponse greet(){
//        GreetResponse response =  new GreetResponse("hello",
//                List.of("Java", "Golang", "JScript"),
//                new Person("Alex", 28,30000));
//        return  response;
//    }
//
//    record Person(String name, int age, double savings){}
//    record GreetResponse(
//            String greet,
//            List<String> faveProgrammingLanguages,
//            Person person){}

//    class GreetResponse{
//        private final String greet;
//
//        GreetResponse(String greet) {
//            this.greet = greet;
//        }
//
//        public String getGreet() {
//            return greet;
//        }
//
//        @Override
//        public String toString() {
//            return "GreetResponse{" +
//                    "greet='" + greet + '\'' +
//                    '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GreetResponse that = (GreetResponse) o;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(greet);
//        }
//    }
}
