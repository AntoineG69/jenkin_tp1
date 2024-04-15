package com.yourcompany.controller;

import com.yourcompany.model.Contact;
import com.yourcompany.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add-contact";
    }

    @PostMapping("/contacts")
    public String addContact(@ModelAttribute Contact contact, Model model) {
        contactService.saveContact(contact);
        model.addAttribute("contacts", contactService.findAllContacts());
        return "contacts";
    }

    @GetMapping("/contacts")
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.findAllContacts());
        return "contacts";
    }

    @GetMapping("contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/contacts";
    }


    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.findContactById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "edit-contact";
        }
        return "redirect:/contacts";
    }

    @PostMapping("/contacts/edit/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute Contact contact, Model model) {
        contact.setId(id);
        contactService.saveContact(contact);
        return "redirect:/contacts";
    }
}
