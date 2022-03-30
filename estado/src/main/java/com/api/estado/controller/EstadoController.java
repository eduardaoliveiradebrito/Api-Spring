package com.api.estado.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.estado.dto.EstadoDto;
import com.api.estado.models.EstadoModel;
import com.api.estado.service.EstadoService;

import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.api.estado.service.EstadoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

//@ComponentScan(basePackages = {"com.api.estado.controller.EstadoController"}) 
@RequestMapping("/estado") 
public class EstadoController {

	final EstadoService estadoService;

	public EstadoController(EstadoService estadoService) {
		this.estadoService = estadoService;
	}


	@PostMapping
	public ResponseEntity<Object> saveEstados(@RequestBody @Valid EstadoDto estadoDto){
		
		if(estadoService.existsByNome(estadoDto.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito no nome do estado, ja estar em uso.");
		}
		
		var estadoModel = new EstadoModel();
		BeanUtils.copyProperties(estadoDto, estadoModel);                 
		estadoModel.setData_criacao(LocalDateTime.now(ZoneId.of("UTC"))); 

		return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.save(estadoModel));
	}
	
	
	/**
	 * nao recebe paramentro
	 * @return listagem dos estados, independente se estar vazia.
	 */
	@GetMapping
	public ResponseEntity<List<EstadoModel>> getAllEStados(){
		return ResponseEntity.status(HttpStatus.OK).body(estadoService.findAll());
	}
	
	/**
	 * Metodo para ler os estados e armazenalos no csv
	 * @return uma mensagem com o caminho do csv
	 */
	@GetMapping("/csv")
	public String getEstadosCSV(){
		final String SAMPLE_CSV_FILE = "/var/www/attendance-btg-api/estado/src/sample.csv";
		
		List<EstadoModel> list = new ArrayList<>();
		list = estadoService.findAll();
		
		EstadoModel element = new EstadoModel();
		
		try (
	            BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

	            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                    .withHeader("ID", "ativo", "sigla", "nome", "data_criacao")
	                    .withDelimiter(';'));
	        ) {
			
				for (int i = 0; i<list.size(); i++) {
					element = list.get(i);
					csvPrinter.printRecord(element.getId(), element.getAtivo(), element.getSigla(),
											element.getNome(), element.getData_criacao());

			    }
				csvPrinter.flush();     
	                   
	        } catch (IOException e) {
				e.printStackTrace();
			}
		
		return  "Confira a pasta /var/www/attendance-btg-api/estado/src/";
	}
	
	/**
	 * Metodo para ler um csv, apenas uma coluna especifica
	 * @return retornar uma lista com os dados da leitura do csv
	 */
	@GetMapping("/csvler")
	public ArrayList<String> getCSV(){
		final String SAMPLE_CSV_FILE_PATH = "/var/www/attendance-btg-api/estado/src/Retorno_VGX_20220322.csv";

		
		ArrayList<String> dados = new ArrayList<>();
		
		try (
				Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                    .withHeader("NOME_AUDIO", "IDENTIFICADOR", "CAMINHO_GRAVACAO")
	                    .withIgnoreHeaderCase()
	                    .withDelimiter(';') 
	                    .withTrim());
	        ) {
	            for (CSVRecord csvRecord : csvParser) {
	            	
	                String path = csvRecord.get("CAMINHO_GRAVACAO");
                	
                    if (path != null && path.trim().length() > 0) {
                        dados.add(path.replaceAll("//192.168.25.111/HOME/RECORDINGS", ""));
                    }
                    
                }
                
            } catch (IOException e) {
				e.printStackTrace();
			}
		
		return dados;
	}
 
	

	/**
	 * recebe o id do estado como parametro
	 * @return listagem dos estados, independente se estar vazia.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getEStado(@PathVariable(value = "id") int id){
		Optional<EstadoModel> estadoModelOptional = estadoService.findById(id); 
		
		if(!estadoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estado não existe");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(estadoModelOptional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEstado(@PathVariable(value = "id") int id){
		Optional<EstadoModel> estadoModelOptional = estadoService.findById(id); 
		
		if(!estadoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estado não existe");
		}
		
		estadoService.delete(estadoModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Estado deletado com sucesso.");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateEstado(@PathVariable(value = "id") int id,
												@RequestBody @Valid EstadoDto estadoDto){
		Optional<EstadoModel> estadoModelOptional = estadoService.findById(id);
		
		if(!estadoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estado não existe");
		}
		
		var estadoModel = new EstadoModel();
		BeanUtils.copyProperties(estadoDto, estadoModelOptional);
		estadoModel.setId(estadoModelOptional.get().getId());
		estadoModel.setData_criacao(estadoModelOptional.get().getData_criacao());		
		estadoService.delete(estadoModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(estadoService.save(estadoModel));
	}
}