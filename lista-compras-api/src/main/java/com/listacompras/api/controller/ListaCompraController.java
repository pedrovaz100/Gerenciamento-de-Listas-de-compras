package com.listacompras.api.controller;

import com.listacompras.api.dto.ListaCompraRequestDTO;
import com.listacompras.api.dto.ListaCompraResponseDTO;
import com.listacompras.api.dto.ListaCompraResumoDTO;
import com.listacompras.api.model.ListaCompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/listas")
@RequiredArgsConstructor
@Validated
public class ListaCompraController {

    private final ListaCompraService listaCompraService;

    @GetMapping
    public Page<ListaCompraResponseDTO> listar(Pageable pageable) {
        return listaCompraService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ListaCompraResponseDTO buscar(@PathVariable Long id) {
        return listaCompraService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListaCompraResponseDTO criar(@Valid @RequestBody ListaCompraRequestDTO listaCompra) {
        return listaCompraService.criar(listaCompra);
    }

    @PutMapping("/{id}")
    public ListaCompraResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ListaCompraRequestDTO listaCompra) {
        return listaCompraService.atualizar(id, listaCompra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        listaCompraService.deletar(id);
    }

    @GetMapping("/busca/nome")
    public Page<ListaCompraResponseDTO> buscarPorNome(@RequestParam @NotBlank String nome, Pageable pageable) {
        return listaCompraService.buscarPorNome(nome, pageable);
    }

    @GetMapping("/busca/mercado/{mercadoId}")
    public Page<ListaCompraResponseDTO> buscarPorMercado(@PathVariable Long mercadoId, Pageable pageable) {
        return listaCompraService.buscarPorMercado(mercadoId, pageable);
    }

    @GetMapping("/resumo")
    public Page<ListaCompraResumoDTO> listarResumo(Pageable pageable) {
        return listaCompraService.listarResumo(pageable);
    }
}
