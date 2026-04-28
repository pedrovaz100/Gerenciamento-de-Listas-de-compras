package com.listacompras.api.model;

import com.listacompras.api.dto.ItemCompraResponseDTO;
import com.listacompras.api.dto.ListaCompraRequestDTO;
import com.listacompras.api.dto.ListaCompraResponseDTO;
import com.listacompras.api.dto.ListaCompraResumoDTO;
import com.listacompras.api.dto.MercadoResponseDTO;
import com.listacompras.api.entity.ItemCompra;
import com.listacompras.api.entity.ListaCompra;
import com.listacompras.api.entity.Mercado;
import com.listacompras.api.repository.ListaCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaCompraService {

    private final ListaCompraRepository listaCompraRepository;
    private final MercadoService mercadoService;

    public Page<ListaCompraResponseDTO> listar(Pageable pageable) {
        return listaCompraRepository.findAll(pageable).map(this::toResponse);
    }

    public ListaCompraResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public ListaCompraResponseDTO criar(ListaCompraRequestDTO request) {
        Mercado mercado = mercadoService.buscarEntidadePorId(request.getMercadoId());
        ListaCompra listaCompra = ListaCompra.builder()
                .nome(request.getNome())
                .dataCriacao(request.getDataCriacao())
                .mercado(mercado)
                .build();
        return toResponse(listaCompraRepository.save(listaCompra));
    }

    public ListaCompraResponseDTO atualizar(Long id, ListaCompraRequestDTO request) {
        ListaCompra listaCompra = buscarEntidadePorId(id);
        Mercado mercado = mercadoService.buscarEntidadePorId(request.getMercadoId());

        listaCompra.setNome(request.getNome());
        listaCompra.setDataCriacao(request.getDataCriacao());
        listaCompra.setMercado(mercado);
        return toResponse(listaCompraRepository.save(listaCompra));
    }

    public void deletar(Long id) {
        ListaCompra listaCompra = buscarEntidadePorId(id);
        listaCompraRepository.delete(listaCompra);
    }

    public Page<ListaCompraResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return listaCompraRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toResponse);
    }

    public Page<ListaCompraResponseDTO> buscarPorMercado(Long mercadoId, Pageable pageable) {
        return listaCompraRepository.findByMercadoId(mercadoId, pageable).map(this::toResponse);
    }

    public Page<ListaCompraResumoDTO> listarResumo(Pageable pageable) {
        return listaCompraRepository.findAllProjectedBy(pageable)
                .map(item -> new ListaCompraResumoDTO(item.getId(), item.getNome()));
    }

    public ListaCompra buscarEntidadePorId(Long id) {
        return listaCompraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de compras com id " + id + " não encontrada"));
    }

    private ListaCompraResponseDTO toResponse(ListaCompra listaCompra) {
        Mercado mercado = listaCompra.getMercado();
        MercadoResponseDTO mercadoResponse = MercadoResponseDTO.builder()
                .id(mercado.getId())
                .nome(mercado.getNome())
                .endereco(mercado.getEndereco())
                .build();

        List<ItemCompraResponseDTO> itens = listaCompra.getItens() == null
                ? Collections.emptyList()
                : listaCompra.getItens().stream().map(this::toItemResponse).toList();

        return ListaCompraResponseDTO.builder()
                .id(listaCompra.getId())
                .nome(listaCompra.getNome())
                .dataCriacao(listaCompra.getDataCriacao())
                .mercado(mercadoResponse)
                .itens(itens)
                .build();
    }

    private ItemCompraResponseDTO toItemResponse(ItemCompra item) {
        return ItemCompraResponseDTO.builder()
                .id(item.getId())
                .nome(item.getNome())
                .quantidade(item.getQuantidade())
                .preco(item.getPreco())
                .listaCompraId(item.getListaCompra().getId())
                .build();
    }
}
