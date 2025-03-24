insert into company_db.public.p_company (
    id,
    created_date_time,
    modified_date_time,
    hub_id,
    company_admin_id,
    name,
    type,
    address
) VALUES (
             '4d5daff0-bf88-47a8-906d-9a1890476668', -- id (UUID)
             '2025-03-23 23:49:29.160364',           -- created_at (from BaseEntity)
             '2025-03-23 23:49:29.160364',
             '3d5575b4-08a9-4af8-9446-bc3aa8df4a4a', -- hub_id
             135,                                    -- company_admin_id
             'test2025',                             -- name
             'sell',                                 -- type
             '서울특별시 송파구 송파대로 66'           -- address
         );
