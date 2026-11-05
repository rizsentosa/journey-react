INSERT INTO
    products (
        name,
        price,
        stock_quantity,
        image_url,
        is_active
    )
VALUES (
        'Beras Pandan Wangi 5kg',
        65000.00,
        100,
        'https://example.com/images/beras-pandan.jpg',
        TRUE
    ),
    (
        'Minyak Goreng Bimoli 2L',
        32000.00,
        50,
        'https://example.com/images/minyak-bimoli.jpg',
        TRUE
    ),
    (
        'Gula Pasir Gulaku 1kg',
        14500.00,
        200,
        'https://example.com/images/gula-gulaku.jpg',
        TRUE
    ),
    (
        'Kopi Arabika Gayo 200g',
        45000.00,
        75,
        'https://example.com/images/kopi-gayo.jpg',
        TRUE
    ),
    (
        'Teh Celup Sariwangi 25s',
        8500.00,
        150,
        'https://example.com/images/teh-sariwangi.jpg',
        TRUE
    ),
    (
        'Sabun Mandi Lifebuoy',
        5000.00,
        300,
        'https://example.com/images/sabun-lifebuoy.jpg',
        TRUE
    ),
    (
        'Pasta Gigi Pepsodent',
        12000.00,
        120,
        'https://example.com/images/pepsodent.jpg',
        TRUE
    ),
    (
        'Mie Instan Indomie Goreng',
        3500.00,
        500,
        'https://example.com/images/indomie.jpg',
        TRUE
    ),
    (
        'Susu UHT Ultra Milk 1L',
        18000.00,
        80,
        'https://example.com/images/ultra-milk.jpg',
        TRUE
    ),
    (
        'Kecap Manis Bango 275ml',
        22000.00,
        90,
        'https://example.com/images/kecap-bango.jpg',
        TRUE
    );

-- Cart Item 1: 2 Beras Pandan Wangi
INSERT INTO
    carts (product_id, quantity)
VALUES (
        (
            SELECT id
            FROM products
            WHERE
                name = 'Beras Pandan Wangi 5kg'
            LIMIT 1
        ),
        2
    );

-- Cart Item 2: 1 Minyak Goreng Bimoli
INSERT INTO
    carts (product_id, quantity)
VALUES (
        (
            SELECT id
            FROM products
            WHERE
                name = 'Minyak Goreng Bimoli 2L'
            LIMIT 1
        ),
        1
    );

-- Cart Item 3: 5 Mie Instan Indomie
INSERT INTO
    carts (product_id, quantity)
VALUES (
        (
            SELECT id
            FROM products
            WHERE
                name = 'Mie Instan Indomie Goreng'
            LIMIT 1
        ),
        5
    );