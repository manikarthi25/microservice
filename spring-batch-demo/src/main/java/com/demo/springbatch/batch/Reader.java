package com.demo.springbatch.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import com.demo.springbatch.entity.UserEO;

public class Reader extends FlatFileItemReader<UserEO> {

	public Reader(Resource resource) {

		super();
		setResource(resource);

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "userid", "username", "email" });
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);

		BeanWrapperFieldSetMapper<UserEO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(UserEO.class);

		DefaultLineMapper<UserEO> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		setLineMapper(defaultLineMapper);

	}

}
