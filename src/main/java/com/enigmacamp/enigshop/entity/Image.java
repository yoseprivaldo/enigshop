package com.enigmacamp.enigshop.entity;

import com.enigmacamp.enigshop.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.results.graph.Fetch;

@Setter
@Getter
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.IMAGE)
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "content-type", nullable = false)
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
