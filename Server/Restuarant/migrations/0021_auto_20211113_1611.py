# Generated by Django 3.2.9 on 2021-11-13 16:11

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Restuarant', '0020_auto_20211113_1610'),
    ]

    operations = [
        migrations.AlterField(
            model_name='beverages',
            name='price',
            field=models.DecimalField(decimal_places=3, max_digits=7),
        ),
        migrations.AlterField(
            model_name='employeesalary',
            name='salary',
            field=models.DecimalField(decimal_places=3, max_digits=7),
        ),
        migrations.AlterField(
            model_name='menu',
            name='discount',
            field=models.DecimalField(decimal_places=3, max_digits=7),
        ),
        migrations.AlterField(
            model_name='menu',
            name='set_menu_price',
            field=models.DecimalField(decimal_places=3, max_digits=7),
        ),
    ]
