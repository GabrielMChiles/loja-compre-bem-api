package com.senai.jonatas.comprebem.controller;

import com.senai.jonatas.comprebem.dto.ProdutoRequestDTO;
import com.senai.jonatas.comprebem.dto.ProdutoResponseDTO;
import com.senai.jonatas.comprebem.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponseDTO> findAll() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO findById(@PathVariable UUID id) {
        return produtoService.findById(id);
    }

    @GetMapping("/filtro")
    public Page<ProdutoResponseDTO> findByFiltro(
            @RequestParam(defaultValue = "") String filtro,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        // Blindagem de Segurança: Impede estritamente que o tamanho exceda 50 itens
        int tamanhoMaximo = Math.min(pageable.getPageSize(), 50);
        Pageable pageableSeguro = PageRequest.of(
                pageable.getPageNumber(),
                tamanhoMaximo,
                pageable.getSort()
        );

        return produtoService.findByFiltro(filtro, pageableSeguro);
    }

    @PostMapping
    public ProdutoResponseDTO save(@Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoResponseDTO update(
            @PathVariable UUID produtoId,
            @Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.update(produtoId, produto);
    }

    @DeleteMapping("/{produtoId}")
    public void delete(@PathVariable UUID produtoId) {
        produtoService.delete(produtoId);
    }
}