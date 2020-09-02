-- Customer
INSERT INTO public.customer(name) VALUES ('Test Customer');

-- Operating System
INSERT INTO public.operating_system(system_name) VALUES ('Windows');
INSERT INTO public.operating_system(system_name) VALUES ('Mac');
INSERT INTO public.operating_system(system_name) VALUES ('Linux');

-- Service
INSERT INTO public.service( service_name, cost, selectable) VALUES ('Register Device', 4.00, false);
INSERT INTO public.service( service_name, selectable) VALUES ('Antivirus', true);
INSERT INTO public.service( service_name, cost, selectable, operating_system_id, parent_id)
	VALUES ('Antivirus Windows', 5.00, false,
			(select id from public.operating_system where system_name = 'Windows'),
			(select id from public.service where service_name = 'Antivirus'));
INSERT INTO public.service( service_name, cost, selectable, operating_system_id, parent_id)
	VALUES ('Antivirus Mac', 7.00, false,
			(select id from public.operating_system where system_name = 'Mac'),
			(select id from public.service where service_name = 'Antivirus'));
INSERT INTO public.service( service_name, cost, selectable) VALUES ('Cloudberry', 3.00, true);
INSERT INTO public.service( service_name, cost, selectable) VALUES ('PSA', 2.00, true);
INSERT INTO public.service( service_name, cost, selectable) VALUES ('TeamViewer', 1.00, true);

