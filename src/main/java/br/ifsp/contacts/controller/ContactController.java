package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe responsável por mapear as rotas/endpoints relacionados
 * aos contatos. Cada método abaixo corresponde a uma operação
 * em nosso sistema.
 */
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));

        existingContact.setNome(updatedContact.getNome());
        existingContact.setTelefone(updatedContact.getTelefone());
        existingContact.setEmail(updatedContact.getEmail());

        return contactRepository.save(existingContact);
    }

    @PatchMapping("/{id}")
    public Contact patchContact(@PathVariable Long id, @RequestBody Contact partialContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));

        if (partialContact.getNome() != null) {
            existingContact.setNome(partialContact.getNome());
        }
        if (partialContact.getTelefone() != null) {
            existingContact.setTelefone(partialContact.getTelefone());
        }
        if (partialContact.getEmail() != null) {
            existingContact.setEmail(partialContact.getEmail());
        }

        return contactRepository.save(existingContact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

    // Exemplo de URL: http://localhost:8080/api/contacts/search?name=Maria
    @GetMapping("/search")
    public List<Contact> searchContactsByName(@RequestParam String name) {
        return contactRepository.findByNomeContainingIgnoreCase(name);
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
}
