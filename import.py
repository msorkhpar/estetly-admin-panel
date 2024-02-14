import pandas as pd
import psycopg2
import numpy as np

# Database connection parameters
conn_params = {
    'dbname': 'Estetly',
    'user': 'Estetly',
    'password': '',
    'host': 'localhost'
}

# Connect to your PostgreSQL database
conn = psycopg2.connect(**conn_params)
cursor = conn.cursor()

# Read the Excel file
df = pd.read_csv('./concerns.csv')
# Dictionaries to keep track of foreign keys and IDs
categories = {}
areas = {}
sub_areas = {}
concerns = {}
for index, row in df.iterrows():
    def get_next_id():
        cursor.execute("SELECT nextval('sequence_generator')")
        return cursor.fetchone()[0]


    # Process the category
    model = row['MODEL']
    category_name = row['CATEGORY']
    if category_name not in categories:
        if model == 'Model 1':
            model = "MODEL_1"
        elif model == 'Model 2':
            model = "MODEL_2"
        elif model == 'Model 3':
            model = "MODEL_3"
        elif model == 'Model 4':
            model = "MODEL_4"

        cursor.execute("INSERT INTO category (id,name, model) VALUES (%s,%s, %s) RETURNING id",
                       (get_next_id(), category_name, model))
        category_id = cursor.fetchone()[0]
        categories[category_name] = category_id
        conn.commit()
    else:
        category_id = categories[category_name]

    # Process the area
    area_name = row['AREA']
    area_id = None
    if area_name and area_name not in areas:
        cursor.execute("INSERT INTO body_area (id, display_name) VALUES (%s, %s) RETURNING id",
                       (get_next_id(), area_name))
        area_id = cursor.fetchone()[0]
        areas[area_name] = area_id
        conn.commit()
    elif area_name:
        area_id = areas[area_name]

    # Process the sub-area as a child of area
    sub_area_name = row['SUB AREA']
    sub_area_id = None

    if type(sub_area_name) is str:
        if sub_area_name not in sub_areas:
            parent_id = area_id if area_name else None
            cursor.execute("INSERT INTO body_area (id, display_name, parent_id) VALUES (%s, %s, %s) RETURNING id",
                           (get_next_id(), sub_area_name, parent_id))
            sub_area_id = cursor.fetchone()[0]
            sub_areas[sub_area_name] = sub_area_id
            conn.commit()
        elif sub_area_name:
            sub_area_id = sub_areas[sub_area_name]

    # Process the concern
    concern_name = row['CONCERN NAME']
    concern_id = None
    if concern_name and concern_name not in concerns:
        # Assuming 'Concern' has 'title', 'category_id' and 'area_id' columns
        cursor.execute("INSERT INTO concern (id, title, category_id, gender) VALUES (%s, %s, %s, %s) RETURNING id",
                       (get_next_id(), concern_name, category_id, "ALL"))
        concern_id = cursor.fetchone()[0]
        concerns[concern_name] = concern_id
        conn.commit()
    elif concern_name:
        concern_id = concerns[concern_name]

    if concern_id is None or (area_id is None and sub_area_id is None):
        continue
    cursor.execute(
        "INSERT INTO body_area_concern_association (id,concern_id, body_area_id) VALUES (%s, %s, %s) RETURNING id",
        (get_next_id(), concern_id, sub_area_id if sub_area_id else area_id))
    conn.commit()

# Close the database connection
cursor.close()
conn.close()
