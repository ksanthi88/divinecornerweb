package com.santhi.divinecornerweb;

import com.santhi.divinecornerweb.controller.AdminController;
import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showEditProductForm_ShouldReturnEditProductView() {

        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Divine Pooja Mandir");

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        String viewName = adminController.showEditProductForm(productId, model);

        assertEquals("edit-product", viewName);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void updateProduct_ShouldRedirectToProductList() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Updated Mandir");

        String viewName = adminController.updateProduct(productId, product);

        assertEquals("redirect:/products", viewName);
        verify(productService, times(1)).updateProduct(productId, product);
    }

    @Test
    void deleteProduct_ShouldRedirectToAdminDashboard() {

        Long productId = 1L;

        String viewName = adminController.deleteProduct(productId);

        assertEquals("redirect:/products", viewName);
        verify(productService, times(1)).deleteProduct(productId);
    }
}