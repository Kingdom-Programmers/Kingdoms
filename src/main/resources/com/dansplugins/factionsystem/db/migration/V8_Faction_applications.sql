create table `mf_faction_application`(
    `faction_id` varchar(36) not null,
    `player_id` varchar(36) not null,
    primary key(`faction_id`, `player_id`),
    foreign key(`faction_id`) references `mf_faction`(`id`) on delete cascade,
    foreign key(`player_id`) references `mf_player`(`id`) on delete cascade
);