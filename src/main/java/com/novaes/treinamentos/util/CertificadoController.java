package com.novaes.treinamentos.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

	/*
	 * Metodo para Buscar Certificados
	 * 
	 * @Autowired private FuncionarioService funcionarioService;
	 * 
	 * @GetMapping("/gerar/{id}")
	 * 
	 * @ResponseBody public void gerarCertificado(@PathVariable Long id,
	 * HttpServletResponse response) throws IOException { // Buscar o funcionário
	 * pelo ID Funcionario funcionario = funcionarioService.buscarPorId(id);
	 * 
	 * // Caminho do modelo de certificado String modeloPath =
	 * "caminho/para/Modelo de certificado - NR 06.pptx";
	 * 
	 * // Gerar o certificado personalizado try (FileInputStream fis = new
	 * FileInputStream(modeloPath); XMLSlideShow ppt = new XMLSlideShow(fis)) {
	 * 
	 * for (XSLFSlide slide : ppt.getSlides()) { for (XSLFTextShape shape :
	 * slide.getShapes()) { if (shape.getText() != null) { String texto =
	 * shape.getText();
	 * 
	 * // Substituir placeholders pelos dados do funcionário texto =
	 * texto.replace("(xxxxxxxx)", funcionario.getRg()) .replace("xxx.xxx.xxx-xx",
	 * funcionario.getCpf()) .replace("Certificamos que ,", "Certificamos que " +
	 * funcionario.getNome() + ",");
	 * 
	 * shape.setText(texto); } } }
	 * 
	 * // Configurar a resposta para download response.setContentType(
	 * "application/vnd.openxmlformats-officedocument.presentationml.presentation");
	 * response.setHeader("Content-Disposition", "attachment; filename=certificado_"
	 * + funcionario.getNome() + ".pptx");
	 * 
	 * // Enviar o arquivo para o cliente ppt.write(response.getOutputStream()); } }
	 */
}
