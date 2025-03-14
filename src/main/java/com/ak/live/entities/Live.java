package com.ak.live.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ak.live.enums.Genre;
import com.ak.live.enums.Language;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LIVE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Live {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String liveName;

    private Integer duration;

    @Column(scale = 2)
    private Double rating;

    private Date releaseDate;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    @OneToMany(mappedBy = "live",cascade = CascadeType.ALL)
    private List<Show> shows = new ArrayList<>();
}
