TRUNCATE
    tl_tour_log,
    td_tour_difficulty,
    tp_tour_point,
    t_tour,
    tt_tour_type;

-- Restore default tour types
INSERT INTO tt_tour_type(tt_id)
VALUES ('BIKE'),
       ('HIKE'),
       ('RUNNING'),
       ('VACATION');
