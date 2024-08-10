package com.Anikets.major.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Anikets.major.dto.ProductDTO;
import com.Anikets.major.model.Category;
import com.Anikets.major.model.Product;
import com.Anikets.major.service.CategoryService;
import com.Anikets.major.service.ProductService;

@Controller 
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/ProductImages";
	
  
	@Autowired
      CategoryService catService;
     @Autowired 
     ProductService prodService;
     @GetMapping("/admin")
     public String adminhome() {
    	 return "adminHome";
     }
	  @GetMapping("/admin/categories") 
	  public String getCategory(Model model) {
	  model.addAttribute("categories", catService.getAllCategory()); 
	  return "categories"; 
	  }
	  
	  @GetMapping("/admin/categories/add") 
	  public String getAddCategory(Model model){ 
	  model.addAttribute("category", new Category()); 
	  return "categoriesAdd"; } 
	  
	  @PostMapping("/admin/categories/add") 
	  public String postAddCategory(@ModelAttribute("category") Category category) {
	  catService.addCategory(category); 
	  return "redirect:/admin/categories"; 
	  }
	  @GetMapping("/admin/categories/delete/{id}") 
	  public String deleteCat(@PathVariable int id) {
	  catService.removeCategoryById(id); 
	  return "redirect:/admin/categories"; 
	  }
	  @GetMapping("/admin/categories/update/{id}")
	  public String updateCat(@PathVariable int id,Model model){
		  Optional<Category> category=catService.getCategoryById(id);
		  if(category.isPresent()) {
			  model.addAttribute("category", category.get());
			  return "categoriesAdd";
		  }else {
				return "404";

		  }
	  }
	  //Product Section
	  @GetMapping("/admin/products") 
	  public String getProduct(Model model) {
	  model.addAttribute("products", prodService.getAllProduct()); 
	  return "products"; 
	  }
	  @GetMapping("/admin/products/add") 
	  public String getProductAdd(Model model) {
	  model.addAttribute("productDTO", new ProductDTO()); 
	  model.addAttribute("categories", catService.getAllCategory());
	  return "productsAdd"; 
	  }
	  @PostMapping("/admin/products/add")
	  public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
			                       @RequestParam("productImage") MultipartFile file,
			                       @RequestParam("imgName") String imgName) throws IOException{
		 Product product =new Product();
		 product.setId(productDTO.getId());
		 product.setName(productDTO.getName());
		 product.setCategory(catService.getCategoryById( productDTO.getCategoryId()).get());
		 product.setPrice(productDTO.getPrice());
		 product.setWeight(productDTO.getWeight());
		 product.setDescription(productDTO.getDescription());
		 String imageUUID;
			
			  if(!file.isEmpty()) { imageUUID=file.getOriginalFilename(); Path
			  filePathAndName=Paths.get(uploadDir,imageUUID);
			  
			  //Path filePathAndName=Paths.get(uploadDir, imageUUID);
			  Files.write(filePathAndName,file.getBytes()); }else { imageUUID=imgName; }
			 	 
		 
		 product.setImageName(imageUUID); 
		 prodService.addProduct(product);
		 return "redirect:/admin/products";
	  }
	  @GetMapping("/admin/product/delete/{id}") 
	  public String deleteProduct(@PathVariable long id) {
	  prodService.removeProductById(id); 
	  return "redirect:/admin/products"; 
	  }
	  @GetMapping("/admin/product/update/{id}") 
	  public String updateProduct(@PathVariable long id,Model model) {
	  Product product=prodService.getProductById(id).get();
	  ProductDTO productDTO=new ProductDTO();
	  productDTO.setId(product.getId());
	  productDTO.setName(product.getName());
	  productDTO.setDescription(product.getDescription());
	  productDTO.setCategoryId(product.getCategory().getId());
	  productDTO.setPrice(product.getPrice());
	  productDTO.setWeight(product.getWeight());
	  productDTO.setImageName(product.getImageName());
	  model.addAttribute("categories",catService.getAllCategory());
	  model.addAttribute("productDTO",productDTO);
	  return "productsAdd"; 
	  }
	  
	 
}
