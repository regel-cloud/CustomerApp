package regel.service;

import regel.models.Address;

import java.util.List;

public interface AddressService {

    List<Address> showAddressList();

    Address showAddressById(long id);

    void saveAddress(Address address);

    void updateAddress(Address updatedAddress, long id);

    void deleteAddress(long id);
}
