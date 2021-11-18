# Generated by Django 3.2.9 on 2021-11-12 17:19

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('Restuarant', '0013_alter_employeesalary_user_id'),
    ]

    operations = [
        migrations.AlterField(
            model_name='dishes_fooditems',
            name='dish_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.dishes'),
        ),
        migrations.AlterField(
            model_name='dishes_fooditems',
            name='food_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.fooditems'),
        ),
        migrations.AlterField(
            model_name='employeesalary',
            name='user_id',
            field=models.OneToOneField(default=0, on_delete=django.db.models.deletion.DO_NOTHING, primary_key=True, serialize=False, to='Restuarant.users'),
        ),
        migrations.AlterField(
            model_name='loyalty',
            name='user_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.users'),
        ),
        migrations.AlterField(
            model_name='menuitem',
            name='menu_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.menu'),
        ),
        migrations.AlterField(
            model_name='orderlineitems',
            name='menu_item',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.menuitem'),
        ),
        migrations.AlterField(
            model_name='orderlineitems',
            name='order_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.orders'),
        ),
        migrations.AlterField(
            model_name='orders',
            name='user_id',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.DO_NOTHING, to='Restuarant.users'),
        ),
    ]
