package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;


    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        Contact contact = contactRepository.findById(address.getContact().getId())
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        address.setContact(contact); 
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.ok(savedAddress);
    }

    @GetMapping("/contacts/{id}")
    public List<Address> getAddressesByContact(@PathVariable Long id) {
        return addressRepository.findByContactId(id);
    }
}
