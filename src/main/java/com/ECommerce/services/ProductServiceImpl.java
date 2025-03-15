package com.ECommerce.services;

import com.ECommerce.exceptions.ApiException;
import com.ECommerce.exceptions.ResourceNotFoundException;
import com.ECommerce.models.Category;
import com.ECommerce.models.Product;
import com.ECommerce.payload.ProductDTO;
import com.ECommerce.payload.ProductResponse;
import com.ECommerce.repositories.CategoryRepository;
import com.ECommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    
    @Value("${project.image}")
    private String path;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ModelMapper modelMapper,
                              FileService fileService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> products = category.getProducts();
        for (Product value: products){
            if(value.getProductName().equals(productDTO.getProductName())){
                throw new ApiException("product already exist");
            }
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() / 100) * (product.getPrice()));
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndSortOrder = (sortOrder.equals("asc"))?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber,pageSize,sortByAndSortOrder);

        Page<Product> productPage = productRepository.findAll(pageRequest);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) throw new ApiException("No Products Available!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sortByAndSortOrder = (sortOrder.equals("asc"))?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber,pageSize,sortByAndSortOrder);

        Page<Product> productPage = productRepository.findByCategory(category, pageRequest);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) throw new ApiException("No products Available!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndSortOrder = (sortOrder.equals("asc"))?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber,pageSize,sortByAndSortOrder);

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%", pageRequest);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) throw new ApiException("No products Available!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        productFromDb.setProductName(productDTO.getProductName());
        productFromDb.setQuantity(productDTO.getQuantity());
        productFromDb.setPrice(productDTO.getPrice());
        productFromDb.setDiscount(productDTO.getDiscount());
        productFromDb.setDescription(productDTO.getDescription());
        double specialPrice = productFromDb.getPrice() - ((productFromDb.getDiscount() / 100) * (productFromDb.getPrice()));
        productFromDb.setSpecialPrice(specialPrice);

        return modelMapper.map(productRepository.save(productFromDb), ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(productToDelete);

        return modelMapper.map(productToDelete, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product","productId", productId));

        String fileName = fileService.uploadImage(path, image);

        productFromDb.setImage(fileName);
        Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
