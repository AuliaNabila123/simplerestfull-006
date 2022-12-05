/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.projectbuildrestful;

import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
public class restController {
    private static Map<String, Product> productRepo = new HashMap<>();
    static{
        //Membuat id dan nama produk
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
    }
    //Menghapus data
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        //Jika data tidak ada makan akan tampil "Product deleted not found"
        if(!productRepo.containsKey(id)){
           return new ResponseEntity<>("Product deleted not found", HttpStatus.NOT_FOUND);
       }
           productRepo.remove(id);
           return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.NOT_FOUND);
    }
    //Update data
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
      //jika data tidak ada maka akan tampil "Product updated not found"
        if(!productRepo.containsKey(id)){
           return new ResponseEntity<>("Product updated not found", HttpStatus.NOT_FOUND);
       }
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        //Mengembalikan dan menampilkan data
        return new ResponseEntity<>("Product is update successfully", HttpStatus.OK);
    }
    //membuat atau menambah data
    @RequestMapping(value = "/products", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@RequestBody @Validated Product product) {
      if(productRepo.containsKey(product.getId())){
           return new ResponseEntity<>("Product not duplicate", HttpStatus.OK);
       }
           productRepo.put(product.getId(), product);
           //mengembalikan dan menampilkan data
           return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
   }
    
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
    
}