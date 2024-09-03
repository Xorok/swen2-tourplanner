CREATE TABLE t_tour
(
    t_id         UUID PRIMARY KEY,
    t_name       VARCHAR(255) NOT NULL,
    t_desc       VARCHAR(255) NOT NULL,
    t_from       VARCHAR(255) NOT NULL,
    t_to         VARCHAR(255) NOT NULL,
    t_tt_type    VARCHAR(255) NOT NULL,
    t_distance_m INTEGER      NULL,
    t_time_min   INTEGER      NULL,
    t_route_img  VARCHAR(255) NULL
);

CREATE TABLE tp_tour_point
(
    tp_t_tour UUID REFERENCES t_tour (t_id),
    tp_num    BIGINT check (tp_num >= 0),
    tp_point  POINT NOT NULL,
    PRIMARY KEY (tp_t_tour, tp_num)
);

CREATE TABLE tl_tour_log
(
    tl_id            UUID PRIMARY KEY,
    tl_t_tour        UUID             NOT NULL REFERENCES t_tour (t_id),
    tl_timestamp     timestamptz(255) NOT NULL,
    tl_comment       VARCHAR(255)     NOT NULL,
    tl_distance_m    INTEGER          NOT NULL check (tl_distance_m >= 0),
    tl_time_min      INTEGER          NOT NULL check (tl_time_min >= 0),
    tl_td_difficulty VARCHAR(255)     NOT NULL,
    tl_rating        INTEGER          NOT NULL check (tl_rating > 0 AND tl_rating <= 100)
);
