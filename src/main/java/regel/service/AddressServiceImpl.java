package regel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regel.dao.AddressDAO;
import regel.models.Address;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDAO repository;

    @Autowired
    public AddressServiceImpl(AddressDAO repository) {
        this.repository = repository;
    }

    @Override
    public List<Address> showAddressList() {
        return repository.findAll();
    }

    @Override
    public Address showAddressById(long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void saveAddress(Address address) {
        repository.save(address);
    }

    @Override
    public void updateAddress(Address updatedAddress, long id) {
        Address addressForUpdating = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        addressForUpdating.setCountry(updatedAddress.getCountry());
        addressForUpdating.setRegion(updatedAddress.getRegion());
        addressForUpdating.setCity(updatedAddress.getCity());
        addressForUpdating.setStreet(updatedAddress.getStreet());
        addressForUpdating.setHouse(updatedAddress.getHouse());
        addressForUpdating.setFlat(updatedAddress.getFlat());
    }

    @Override
    public void deleteAddress(long id) {
        repository.deleteById(id);
    }
}
