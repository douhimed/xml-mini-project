package org.mql.controllers;

import org.mql.dao.IDAO;
import org.mql.models.Entree;
import org.mql.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RootController {

	@Autowired
	private IDAO dao;

	@GetMapping(path = { "/", "/entrees" })
	public String homePage(Model model) {
		model.addAttribute("entrees", dao.getAll());
		return "home";
	}

	@GetMapping("/entrees/{entreeID}")
	public String getEntree(Model model, @PathVariable String entreeID) {
		Entree entree = dao.getById(entreeID);
		if(entree == null)
			return "redirect:/";
		
		String items = String.join(",", entree.getItems());
		model.addAttribute("items", items);
		model.addAttribute("entree", entree);
		return "update";
	}

	@GetMapping("/entrees/delete/{entreeID}")
	public String deleteEntree(@PathVariable String entreeID) {
		dao.delete(entreeID);
		return "redirect:/";
	}

	@GetMapping("/entrees/new")
	public String getAddForm(Model model) {
		model.addAttribute("entree", new Entree());
		return "add";
	}

	@PostMapping("/entrees")
	public String add(Model model, Entree entree, String allItems, @RequestParam MultipartFile picture) {
		String[] items = allItems.split(",");
		for (String item : items) {
			if (!(item.equals("") && item.equals(null)))
				entree.addItem(item.trim());
		}

		if (picture.isEmpty())
			entree.setImg("default.jpg");
		else
			entree.setImg(Utilities.upload("images", picture));

		dao.add(entree);
		return "redirect:/";
	}

	@PutMapping("/entrees")
	public String update(Model model, Entree entree, String allItems, @RequestParam MultipartFile picture) {
		String[] items = allItems.split(",");
		for (String item : items) {
			entree.addItem(item.trim());
		}

		if (!picture.isEmpty())
			entree.setImg(Utilities.upload("images", picture));

		dao.update(entree);
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String search(Model model, String section) {
		model.addAttribute("keyWord", section);
		model.addAttribute("entrees", dao.findBySection(section));
		return "home";
	}

}
