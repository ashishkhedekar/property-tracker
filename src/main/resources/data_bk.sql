INSERT INTO LOCATIONS ( id, code, name , description, main_Image, thumbnail) values
(1, 'REGION%5E239', 'Buckingham', 'Buckingham', 'https://www.bucklitfest.org/wp-content/uploads/2020/02/5-1410x705.jpg', 'https://www.bucklitfest.org/wp-content/uploads/2020/02/5-1410x705.jpg')

select property_id from PROPERTIES


select property_id, count(property_id) from PROPERTIES group by property_id


select * from PROPERTIES where property_id = '78897357'
