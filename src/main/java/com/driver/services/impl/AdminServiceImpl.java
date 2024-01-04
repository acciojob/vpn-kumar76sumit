package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.driver.model.CountryName.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Admin admin=adminRepository1.findById(adminId).get();
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);
        admin.getServiceProviders().add(serviceProvider);
        serviceProviderRepository1.save(serviceProvider);
        return adminRepository1.save(admin);
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        CountryName countryName1=null;
        if(countryName.equalsIgnoreCase("ind")) countryName1=IND;
        else if(countryName.equalsIgnoreCase("aus")) countryName1=AUS;
        else if(countryName.equalsIgnoreCase("usa")) countryName1=USA;
        else if(countryName.equalsIgnoreCase("chi")) countryName1=CHI;
        else if(countryName.equalsIgnoreCase("jpn")) countryName1=JPN;
        else throw new Exception("Country not found");

        ServiceProvider serviceProvider=serviceProviderRepository1.findById(serviceProviderId).get();
        Country country=new Country();
        country.setCountryName(countryName1);
        country.setCode(countryName1.toCode());
        country.setServiceProvider(serviceProvider);
        countryRepository1.save(country);

        serviceProvider.getCountryList().add(country);
        return serviceProviderRepository1.save(serviceProvider);
    }
}
