package com.listacompras.api.controller;

import com.listacompras.api.dto.ItemCompraRequestDTO;
import com.listacompras.api.dto.ItemCompraResponseDTO;
import com.listacompras.api.model.ItemCompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
@Validated
public class ItemCompraController {

    private final ItemCompraService itemCompraService;

    @GetMapping
    public Page<ItemCompraResponseDTO> listar(Pageable pageable) {
        return itemCompraService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ItemCompraResponseDTO buscar(@PathVariable Long id) {
        return itemCompraService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCompraResponseDTO criar(@Valid @RequestBody ItemCompraRequestDTO itemCompra) {
        return itemCompraService.criar(itemCompra);
    }

    @PutMapping("/{id}")
    public ItemCompraResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemCompraRequestDTO itemCompra) {
        return itemCompraService.atualizar(id, itemCompra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        itemCompraService.deletar(id);
    }

    @GetMapping("/busca/nome")
    public Page<ItemCompraResponseDTO> buscarPorNome(@RequestParam @NotBlank String nome, Pageable pageable) {
        return itemCompraService.buscarPorNome(nome, pageable);
    }

    @GetMapping("/busca/preco")
    public Page<ItemCompraResponseDTO> buscarPorFaixaPreco(
            @RequestParam @NotNull @Positive Double precoMin,
            @RequestParam @NotNull @Positive Double precoMax,
            Pageable pageable
    ) {
        return itemCompraService.buscarPorFaixaPreco(precoMin, precoMax, pageable);
    }

    @GetMapping("/busca/quantidade-minima")
    public Page<ItemCompraResponseDTO> buscarPorQuantidadeMinima(
            @RequestParam @NotNull @Positive Integer quantidade,
            Pageable pageable
    ) {
        return itemCompraService.buscarPorQuantidadeMinima(quantidade, pageable);
    }
}
