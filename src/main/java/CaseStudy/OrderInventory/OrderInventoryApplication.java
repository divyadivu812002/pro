package CaseStudy.OrderInventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import CaseStudy.OrderInventory.service.CustomUserDetailsService;
import CaseStudy.OrderInventory.Filter.JwtFilter;

@SpringBootApplication(scanBasePackages="CaseStudy.OrderInventory.controller,CaseStudy.OrderInventory.service")
@EntityScan("CaseStudy.OrderInventory.model")
@EnableJpaRepositories("CaseStudy.OrderInventory.dao")
public class OrderInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderInventoryApplication.class, args);
    }

    @Bean
    @DependsOn("userDetailsService")
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        CustomUserDetailsService service = userDetailsService();
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()

            // StoresController
            .requestMatchers(HttpMethod.GET, "/api/stores").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/stores/{id}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.POST, "/api/stores").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/stores/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/stores/{id}").hasRole("ADMIN")
            
            // InventoryController
            .requestMatchers(HttpMethod.GET, "/inventory/all").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/inventory", "/inventory/shipment").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/inventory/order/{orderId}/details").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/inventory/{orderId}/details").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/inventory/product/{productId}/store/{storeId}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/inventory/shipment/count/{storeId}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.POST,"/inventory/create").hasAnyRole("ADMIN","INVENTORYMANAGER")
            .requestMatchers(HttpMethod.PUT,"/inventory/update/{inventoryId}").hasAnyRole("ADMIN","INVENTORYMANAGER")
            .requestMatchers(HttpMethod.DELETE,"/inventory/delete/{inventoryId}").hasAnyRole("ADMIN","INVENTORYMANAGER")
            // OrderItemsController
            .requestMatchers(HttpMethod.GET, "/orderitems").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/orderitems/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/orderitems").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/orderitems/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/orderitems/{id}").hasRole("ADMIN")

            // OrdersController
            .requestMatchers(HttpMethod.GET, "/api/orders").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/orders/{id}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/orders/customer/{customerId}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/orders/status/{status}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/orders/status/count/{status}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/orders/date/{startDate}/{endDate}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/orders").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/orders").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/orders/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/orders/{id}/cancel").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/orders/customer/email/{email}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/orders/store/{storeName}").hasAnyRole("ADMIN", "STOREMANAGER")

            // ProductsController
            .requestMatchers(HttpMethod.GET, "/api/products").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/{id}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.POST, "/api/products/addproduct").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/products/delete/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/products/brand/{brand}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/name/{name}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/colour/{colour}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/price-range").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/filterByPrice/{minPrice}/{maxPrice}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/api/products/sort/brand").hasAnyRole("ADMIN", "INVENTORYMANAGER")

            // ShipmentsController
            .requestMatchers(HttpMethod.GET, "/shipments").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.GET, "/shipments/{id}").hasAnyRole("ADMIN", "INVENTORYMANAGER")
            .requestMatchers(HttpMethod.POST, "/shipments").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/shipments/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/shipments/{id}").hasRole("ADMIN")

            // CustomersController
            .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/customers").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/customers/{customerId}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/customers/email/{emailId}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/name/{name}").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/{custId}/order").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/{custId}/shipment").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/shipments/pending").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/orders/completed").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/shipments/overdue").hasAnyRole("ADMIN", "STOREMANAGER")
            .requestMatchers(HttpMethod.GET, "/customers/shipment/{status}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/customers/orders/quantity/{min}/{max}").hasRole("ADMIN")
            
            //UserController
            .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/store/manager/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/inventory/manager/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/admin/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
