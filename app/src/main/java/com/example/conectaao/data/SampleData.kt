package com.example.conectaao.data

import com.example.conectaao.R
import com.example.conectaao.model.*
import com.google.android.gms.maps.model.LatLng

/**
 * Classe com dados de exemplo para serem usados no aplicativo
 * até a implementação da conexão com o banco de dados.
 */
object SampleData {
    
    // Posts da comunidade
    val posts = listOf(
        Post(
            id = "1",
            authorName = "Maria Silva",
            content = "Hoje participei de um evento incrível sobre acessibilidade em espaços públicos! Foi muito inspirador ver tantas pessoas engajadas na causa.",
            timestamp = System.currentTimeMillis() - 3600000, // 1 hora atrás
            likes = 15,
            comments = listOf(
                Comment("2", "João Oliveira", "Que legal! Onde foi o evento?", System.currentTimeMillis() - 1800000),
                Comment("3", "Ana Souza", "Também quero participar da próxima!", System.currentTimeMillis() - 900000)
            ),
            imageResId = R.drawable.sample_post_image
        ),
        Post(
            id = "4",
            authorName = "Carlos Ferreira",
            content = "Alguém sabe de algum local acessível para cadeirantes próximo ao centro da cidade? Preciso de recomendações para um amigo que está visitando.",
            timestamp = System.currentTimeMillis() - 86400000, // 1 dia atrás
            likes = 7,
            comments = listOf(
                Comment("5", "Paula Costa", "O Parque Municipal tem rampas de acesso e banheiros adaptados!", System.currentTimeMillis() - 43200000)
            )
        ),
        Post(
            id = "6",
            authorName = "Roberto Almeida",
            content = "Estou disponível para ajudar pessoas idosas com compras e tarefas domésticas na região norte da cidade. Entre em contato se precisar de ajuda!",
            timestamp = System.currentTimeMillis() - 172800000, // 2 dias atrás
            likes = 23,
            comments = listOf()
        )
    )
    
    // Voluntários
    val volunteers = listOf(
        User(
            id = "1",
            name = "Amanda Santos",
            email = "amanda.santos@email.com",
            phone = "(11) 98765-4321",
            bio = "Estudante de fisioterapia apaixonada por ajudar pessoas com mobilidade reduzida",
            specialties = listOf("Acompanhamento", "Fisioterapia", "Transporte"),
            rating = 4.8f,
            distance = 1.5,
            availability = Availability.WEEKENDS,
            profileImageUrl = ""
        ),
        User(
            id = "2",
            name = "Lucas Mendes",
            email = "lucas.mendes@email.com",
            phone = "(11) 91234-5678",
            bio = "Professor aposentado, disponível para ajudar idosos com alfabetização digital",
            specialties = listOf("Educação", "Alfabetização Digital", "Companhia"),
            rating = 4.9f,
            distance = 2.3,
            availability = Availability.AFTERNOONS,
            profileImageUrl = ""
        ),
        User(
            id = "3",
            name = "Fernanda Lima",
            email = "fernanda.lima@email.com",
            phone = "(11) 94567-8901",
            bio = "Enfermeira com experiência em cuidados com idosos e pessoas com deficiência",
            specialties = listOf("Cuidados de Saúde", "Primeiros Socorros", "Acompanhamento Médico"),
            rating = 5.0f,
            distance = 3.0,
            availability = Availability.FULL_TIME,
            profileImageUrl = ""
        ),
        User(
            id = "4",
            name = "Ricardo Gomes",
            email = "ricardo.gomes@email.com",
            phone = "(11) 92345-6789",
            bio = "Motorista voluntário para transporte de pessoas com mobilidade reduzida",
            specialties = listOf("Transporte", "Locomoção", "Compras"),
            rating = 4.7f,
            distance = 4.2,
            availability = Availability.MORNINGS,
            profileImageUrl = ""
        )
    )
    
    // Locais no mapa
    val places = listOf(
        Place(
            id = "1",
            name = "Hospital São Paulo",
            address = "Rua Napoleão de Barros, 715 - Vila Clementino",
            description = "Hospital público com acessibilidade para cadeirantes e atendimento prioritário",
            type = MarkerType.HOSPITAL,
            location = LatLng(-23.5969, -46.6427),
            phone = "(11) 5576-4000",
            website = "https://www.hospitalsaopaulo.org.br",
            isAccessible = true,
            accessibilityFeatures = listOf(
                "Rampas de acesso",
                "Banheiros adaptados",
                "Elevadores",
                "Atendimento prioritário"
            )
        ),
        Place(
            id = "2",
            name = "SAMU SP",
            address = "Rua Santa Cruz, 1191 - Vila Mariana",
            description = "Serviço de Atendimento Móvel de Urgência",
            type = MarkerType.HOSPITAL,
            location = LatLng(-23.5988, -46.6332),
            phone = "192",
            website = "https://www.prefeitura.sp.gov.br/cidade/secretarias/saude/atencao_hospitalar/index.php?p=188701",
            isAccessible = true,
            accessibilityFeatures = listOf(
                "Ambulâncias adaptadas",
                "Atendimento especializado"
            )
        ),
        Place(
            id = "3",
            name = "1º DP - Sé",
            address = "Rua Brigadeiro Tobias, 527 - Centro",
            description = "Delegacia de Polícia com atendimento especializado para pessoas com deficiência",
            type = MarkerType.POLICE,
            location = LatLng(-23.5387, -46.6347),
            phone = "(11) 3311-3315",
            website = "https://www.ssp.sp.gov.br",
            isAccessible = true,
            accessibilityFeatures = listOf(
                "Rampas de acesso",
                "Atendimento prioritário"
            )
        ),
        Place(
            id = "4",
            name = "Corpo de Bombeiros - Quartel Central",
            address = "Praça Clóvis Beviláqua, 421 - Sé",
            description = "Quartel Central do Corpo de Bombeiros de São Paulo",
            type = MarkerType.FIRE_STATION,
            location = LatLng(-23.5475, -46.6361),
            phone = "193",
            website = "https://www.bombeiros.sp.gov.br",
            isAccessible = true,
            accessibilityFeatures = listOf(
                "Rampas de acesso",
                "Equipe treinada para atendimento especial"
            )
        ),
        Place(
            id = "5",
            name = "Parque Ibirapuera",
            address = "Av. Pedro Álvares Cabral - Vila Mariana",
            description = "Parque com estrutura acessível para pessoas com deficiência",
            type = MarkerType.ACCESSIBLE_LOCATION,
            location = LatLng(-23.5874, -46.6576),
            phone = "(11) 5574-5045",
            website = "https://parqueibirapuera.org",
            isAccessible = true,
            accessibilityFeatures = listOf(
                "Rampas de acesso",
                "Banheiros adaptados",
                "Playground adaptado",
                "Piso tátil"
            )
        )
    )
} 