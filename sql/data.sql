--  유저
-- username : master123, password : masterpass123!
INSERT INTO public.p_user (created_date_time, deleted_at, id, modified_date_time, created_by,
                           deleted_by, modified_by, nickname, password, role, slack_username,
                           username)
VALUES (now(),
        null, 1,
        now(),
        null,
        null,
        null,
        'master00',
        '$2a$10$6XDW1fReJcsn3D3v9gl7t.F00GbTK1hvvJlwfpI7vaIqu4N5wELpK',
        'ROLE_MASTER',
        'master_slack',
        'master123'),
       (now(),
        null,
        2,
        now(),
        null,
        null,
        null,
        'store00',
        '$2a$10$wMPn5CgKxydYLaH2PGCwyOtx3bj5lBucZI8dUF.7uCi6ql7nasiBO',
        'ROLE_STORE',
        'store_slack',
        'store123'),
       (now(),
        null,
        3,
        now(),
        null,
        null,
        null,
        'hub00',
        '$2a$10$ijVUF4mysj5WvQhCkRvER.x6z49i9f4Cr/kCc.wYUntvPE8QndT/m',
        'ROLE_HUB',
        'hub_slack',
        'hub123'),
       (now(),
        null,
        4,
        now(),
        null,
        null,
        null,
        '01delivery',
        '$2a$10$GdvtXMkvpHA3ndVSoXGZt.LGQ1Mf/sSAkHrlgMmkdGEi5D4aj06pO',
        'ROLE_DELIVERY',
        'delivery1',
        'delivery_slack1'),
       (now(),
        null,
        5,
        now(),
        null,
        null,
        null,
        '02delivery',
        '$2a$10$/bSi2K.NVP8w5usbSISZJ.V/JHfr87dZiaYCgtjE7jg.CItGKQ3hy',
        'ROLE_DELIVERY',
        'delivery2',
        'delivery_slack2'),
       (now(),
        null,
        6,
        now(),
        null,
        null,
        null,
        '03delivery',
        '$2a$10$F0o5WOMNWYXAD/bfYcXnI.wViQ.ibZlt7PGb1I0EmKjkX7Ba8lKkq',
        'ROLE_DELIVERY',
        'delivery3',
        'delivery_slack3');

--  업체
INSERT INTO company_db.public.p_company (id, created_date_time, modified_date_time, hub_id,
                                         company_admin_id, name, type, address)
VALUES ('3d5575b4-08a9-4af8-9446-bc3aa8df4a4a',
        now(),
        now(),
        '4d5daff0-bf88-47a8-906d-9a1890476668',
        2, -- company_admin_id
        'test2025',
        'sell',
        '서울특별시 송파구 송파대로 66'),
       ('180af423-bb7d-4b3b-b1ae-a720225ebd37',
        now(),
        now(),
        'bb929287-92f4-4e5d-8fe1-77f8213316ba',
        null,
        '라이언상사',
        'string',
        '주소주소'),
       ('4762d050-7960-442e-836a-059079b3e4f0',
        now(),
        now(),
        '4d5daff0-bf88-47a8-906d-9a1890476668',
        null,
        '춘식상사',
        'string',
        '춘식 상사 주소');

--  상품
INSERT INTO public.p_product (product_stock, created_date_time, deleted_at, modified_date_time,
                              hub_id, id, store_id, created_by, deleted_by, modified_by,
                              product_name)
VALUES (7,
        now(),
        null,
        now(),
        '4d5daff0-bf88-47a8-906d-9a1890476668',
        '1b489a01-3771-4529-8ce2-56236926acfa',
        '4762d050-7960-442e-836a-059079b3e4f0',
        'tdest321',
        null,
        'tdest321',
        '감귤춘식'),
       (8,
        now(),
        null,
        now(),
        '4d5daff0-bf88-47a8-906d-9a1890476668',
        'b26bd74f-0c82-4eac-b032-28bc596c77ac',
        '4762d050-7960-442e-836a-059079b3e4f0',
        'tdest321',
        null,
        'tdest321',
        '돌하르방춘식');



