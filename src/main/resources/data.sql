-- Populating WASTE_CATEGORY table
INSERT INTO WASTE_CATEGORY (NAME, DESCRIPTION, RECYCLABLE)
VALUES ('Plastic', 'Recyclable plastic materials', true);

INSERT INTO WASTE_CATEGORY (NAME, DESCRIPTION, RECYCLABLE)
VALUES ('Paper', 'Recyclable paper products', true);

INSERT INTO WASTE_CATEGORY (NAME, DESCRIPTION, RECYCLABLE)
VALUES ('Metal', 'Non-recyclable metal items', false);

INSERT INTO WASTE_CATEGORY (NAME, DESCRIPTION, RECYCLABLE)
VALUES ('Organic', 'Biodegradable organic waste', true);

INSERT INTO WASTE_CATEGORY (NAME, DESCRIPTION, RECYCLABLE)
VALUES ('Glass', 'Non-recyclable glass items', false);

-- Populating DISPOSAL_GUIDELINE table
INSERT INTO DISPOSAL_GUIDELINE (WASTE_CATEGORY_ID, DESCRIPTION, STEPS, LEGAL_REQUIREMENTS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Paper'),
        'Recycle paper products at designated facilities.',
        '1. Collect paper waste.\n2. Take to paper recycling facility.',
        'Ensure compliance with recycling guidelines.');

INSERT INTO DISPOSAL_GUIDELINE (WASTE_CATEGORY_ID, DESCRIPTION, STEPS, LEGAL_REQUIREMENTS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Metal'),
        'Dispose of non-recyclable metal items in landfill.',
        '1. Gather metal items.\n2. Place in landfill disposal.',
        'Follow waste disposal regulations.');

INSERT INTO DISPOSAL_GUIDELINE (WASTE_CATEGORY_ID, DESCRIPTION, STEPS, LEGAL_REQUIREMENTS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Glass'),
        'Dispose of glass waste in designated containers.',
        '1. Collect glass waste.\n2. Place in glass recycling center.',
        'Use glass recycling facilities for environmentally friendly disposal.');

INSERT INTO DISPOSAL_GUIDELINE (WASTE_CATEGORY_ID, DESCRIPTION, STEPS, LEGAL_REQUIREMENTS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Organic'),
        'Compost organic waste for environmental benefits.',
        '1. Collect organic waste.\n2. Create compost pile.',
        'Understand composting regulations and best practices.');

INSERT INTO DISPOSAL_GUIDELINE (WASTE_CATEGORY_ID, DESCRIPTION, STEPS, LEGAL_REQUIREMENTS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Plastic'),
        'Dispose of plastic waste in recycling bins.',
        '1. Collect plastic waste.\n2. Place in designated recycling bin.',
        'Follow local recycling regulations.');

-- Populating RECYCLING_TIP table
INSERT INTO RECYCLING_TIP (WASTE_CATEGORY_ID, TITLE, DESCRIPTION, SOURCE)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Plastic'),
        'Reduce Plastic Usage',
        'Use reusable containers instead of single-use plastic.',
        'Environmental campaign website');

INSERT INTO RECYCLING_TIP (WASTE_CATEGORY_ID, TITLE, DESCRIPTION, SOURCE)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Paper'),
        'Recycle Paper Products',
        'Paper can be recycled multiple times; use paper recycling facilities.',
        'Local recycling initiative');

INSERT INTO RECYCLING_TIP (WASTE_CATEGORY_ID, TITLE, DESCRIPTION, SOURCE)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Metal'),
        'Recycle Metal Items',
        'Metal items are valuable for recycling; take them to metal recycling centers.',
        'National recycling program');

INSERT INTO RECYCLING_TIP (WASTE_CATEGORY_ID, TITLE, DESCRIPTION, SOURCE)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Organic'),
        'Compost Organic Waste',
        'Turn organic waste into nutrient-rich compost for gardening.',
        'Home composting guide');

INSERT INTO RECYCLING_TIP (WASTE_CATEGORY_ID, TITLE, DESCRIPTION, SOURCE)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Glass'),
        'Reuse Glass Containers',
        'Glass containers can be reused multiple times; wash and reuse them.',
        'Glass recycling center website');

-- Populating WASTE_CATEGORY_DISPOSAL_METHODS table
INSERT INTO WASTE_CATEGORY_DISPOSAL_METHODS (WASTE_CATEGORY_ID, DISPOSAL_METHODS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Plastic'), 'Landfill, Recycling plant');

INSERT INTO WASTE_CATEGORY_DISPOSAL_METHODS (WASTE_CATEGORY_ID, DISPOSAL_METHODS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Paper'), 'Landfill, Paper recycling facility');

INSERT INTO WASTE_CATEGORY_DISPOSAL_METHODS (WASTE_CATEGORY_ID, DISPOSAL_METHODS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Metal'), 'Landfill, Metal recycling facility');

INSERT INTO WASTE_CATEGORY_DISPOSAL_METHODS (WASTE_CATEGORY_ID, DISPOSAL_METHODS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Organic'), 'Composting, Landfill (if not composted)');

INSERT INTO WASTE_CATEGORY_DISPOSAL_METHODS (WASTE_CATEGORY_ID, DISPOSAL_METHODS)
VALUES ((SELECT ID FROM WASTE_CATEGORY WHERE NAME = 'Glass'), 'Landfill, Glass recycling center');
